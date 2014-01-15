package it.fmach.metadb.isatab.controller

import it.fmach.metadb.Role;
import it.fmach.metadb.User

class UserController {
	
	def springSecurityService
	
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
		}
		
		// create directories
		
		
		def newUser = new User(username: params['name'], password: params['password'], workDir: params['workDir'])
		
		try{
			newUser.save(flush: true, failOnError: true)
		}catch(Exception e){
			e.printStackTrace()
			flash.error = e.message
		}
	}
	
}
