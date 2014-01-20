package it.fmach.metadb.isatab.controller

import it.fmach.metadb.Role;
import it.fmach.metadb.User
import it.fmach.metadb.UserRole
import it.fmach.metadb.user.UserWorkDirGenerator;

class UserController {
	
	def springSecurityService
	def userService
	
	def index() {
		// list all
		session.userList = User.list()
	}
	
	def newUser(){
		def currentUser = springSecurityService.getCurrentUser()
		session.workDir = grailsApplication.config.metadb.dataPath + "/"
	}
	
	def saveNewUser(){
		def userRole = (params['admin']) ? (Role.findByAuthority('ROLE_ADMIN')) : (Role.findByAuthority('ROLE_USER'))
	
		if(params['password'] != params['retypedPassword']){
			flash.error = "Passwords are differing. Please retype"
			redirect(action: 'newUser')
			return
		}		
		
		def newUser = new User(username: params['name'], password: params['password'], workDir: params['workDir'])
		def dirGenerator = new UserWorkDirGenerator()
		
		try{
			// create directories
			dirGenerator.createWorkDir(params['workDir'])
			
			// and save the user
			newUser.save(flush: true, failOnError: true)
			UserRole.create(newUser, userRole, true)
		}catch(Exception e){
			e.printStackTrace()
			flash.error = e.message
			redirect(action: 'newUser')
			return
		}
		
		flash.message = "New user was created."
		redirect(action: "index")
	}
	
	
	def delete(){
		// delete selected user
		def user = User.get(params.id)
		
		try{
			userService.delete(user)
		}catch(Exception e){
			e.printStackTrace()
			flash.error = e.message
			redirect(action: 'index')
			return
		}
		
		flash.message = "User was deleted"
		redirect(action: 'index')
	}
	
	def detail(){
		// load this instrument if params.id is provided
		if(params.id) session.user = User.get(params.id)

		if(! session.user) throw new RuntimeException("missing params.id")
		
		// check if admin
		session.isAdmin = (session.user.getAuthorities().toList().get(0).authority == 'ROLE_ADMIN') ? ("checked") : null
	}
	
	def saveUserUpdate(){
		// reload user
		def user = session.user
		user.refresh()
		
		def userRole = (params['admin']) ? (Role.findByAuthority('ROLE_ADMIN')) : (Role.findByAuthority('ROLE_USER'))
		
		// check if new password is ok
		if(params['password'] != params['retypedPassword']){
			session.user = user
			flash.error = "Passwords are differing. Please retype"
			redirect(action: 'detail')
			return
		}
		
		user.username = params['name']
		if(params['password']) user.password = params['password']
		
		// check if workDir changed and try to create it if yes
		def dirGenerator = new UserWorkDirGenerator()
		if(params['workDir'] != user.workDir){
			try{
				// create directories
				dirGenerator.createWorkDir(params['workDir'])
			}catch(Exception e){
				session.user = user
				e.printStackTrace()
				flash.error = e.message
				redirect(action: 'detail')
				return
			}
		}

		user.workDir = params['workDir']
			
		try{		
			// and save the user
			user.save(flush: true, failOnError: true)
			
			// delete old role and create new one
			userService.deleteRole(user)
			UserRole.create(user, userRole, true)
		}catch(Exception e){
			session.user = user
			e.printStackTrace()
			flash.error = e.message
			redirect(action: 'detail')
			return
		}
		
		flash.message = "User was updated."
		redirect(action: 'index')
	}
	
}
