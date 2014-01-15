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
		flash.userList = User.list()
	}
	
	def newUser(){
		def currentUser = springSecurityService.getCurrentUser()
		flash.workDir = grailsApplication.config.metadb.dataPath + "/"
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
	
}
