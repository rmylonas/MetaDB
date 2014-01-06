package it.fmach.metadb.isatab.controller

import it.fmach.metadb.isatab.model.FEMGroup
import it.fmach.metadb.isatab.model.FEMProject

class GroupController {

    def index() { 
		// list all groups
		flash.groupList = FEMGroup.list()
	}
	
	def newGroup() {}
	
	def saveNewGroup(){
		// save the new group
		new FEMGroup(name: params.name, description: params.description).save(flush: true)
		flash.message = "New group was created"
		redirect(action: 'index')
	}
	
	def delete(){
		// delete this group
		FEMGroup.get(params.id).delete()
		flash.message = "Group was deleted"
		redirect(action: 'index')
	}
	
	def detail(){
		// load this group
		flash.group = FEMGroup.get(params.id)
	}
	
	def updateGroup(){
		// update the group settings
		def group = FEMGroup.get(flash.group.id)
		group.name = params.name
		group.description = params.description
		
		// save the new settings
		group.save(flush: true)
		flash.message = "Group was updated"
		redirect(action: 'index')
	}
	
	def newProject() {
		flash.groupId = flash.group.id
	}
	
	def saveNewProject(){
		// create the new project
		def newProject = new FEMProject(name: params.name, description: params.description)
		
		// save if valid
		try{
			newProject.save(flush: true, failOnError: true)
		}catch(Exception e){
			flash.error = e.message
			redirect(action: 'detail')
			return
		}
		
		flash.message = "New project was created"
		
		// set the correct id parameter
		params.id = flash.groupId
		redirect(action: 'detail')
	}
	
}
