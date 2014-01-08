package it.fmach.metadb.isatab.controller

import it.fmach.metadb.isatab.model.FEMGroup
import it.fmach.metadb.isatab.model.FEMProject

class GroupController {
	
	def groupService

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
		def group = FEMGroup.get(params.id)
		
		try{
			groupService.delete(group)
		}catch(Exception e){
			e.printStackTrace()
			flash.error = e.message
			redirect(action: 'index')
			return
		}
		
		flash.message = "Group was deleted"
		redirect(action: 'index')
	}
	
	def deleteProject(){
		// load the selected project
		def project = FEMProject.get(params.id)
		def group = FEMGroup.get(session.group.id)
		
		try{
			groupService.deleteProject(group, project)
		}catch(Exception e){
			e.printStackTrace()
			flash.error = e.message
			redirect(action: 'detail')
			return
		}
		
		// re-attach the group to the session
		session.group = group
		
		flash.message = "Project was deleted"
		redirect(action: 'detail')
	}
	
	
	def detail(){
		// load this group if params.id is provided
		if(params.id) session.group = FEMGroup.get(params.id)
			
		if(! session.group) throw new RuntimeException("missing session.group or params.id")
	}
	
	
	def projectDetail(){
		// load this group if params.id is provided
		if(params.id) flash.project = FEMProject.get(params.id)
			
		if(! flash.project) throw new RuntimeException("missing params.id")
	}
	
	
	def updateGroup(){
		// update the group settings
		def group = session.group
		
		group.name = params.name
		group.description = params.description
		
		// save the new settings
		group.save(flush: true)
		flash.message = "Group was updated"
		redirect(action: 'index')
	}
	
	def updateProject(){
		// update the project settings
		def project = FEMProject.get(flash.project.id)
		
		project.name = params.name
		project.description = params.description
		
		// save the new settings
		project.save(flush: true)
		flash.message = "Project was updated"
		redirect(action: 'detail')
	}
	
	
	def newProject() { }
	
	def saveNewProject(){
		// create the new project
		def newProject = new FEMProject(name: params.name, description: params.description)
		
		// the group to add to
		def group = session.group //FEMGroup.get(flash.groupId)
		
		// save if valid
		try{
			group.addToProjects(newProject).save(flush: true, failOnError: true)
		}catch(Exception e){
			flash.error = e.message
			redirect(action: 'detail')
			return
		}

		flash.message = "New project was created"
		
		// set current group
		//flash.group = group
		redirect(action: 'detail')
	}
	
}
