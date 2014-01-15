package it.fmach.metadb.services

import it.fmach.metadb.User
import it.fmach.metadb.UserRole
import it.fmach.metadb.isatab.model.FEMAssay

class UserService {

    def delete(User user) {
		
		// check if the user owns some assays
		def assayList = FEMAssay.findByOwner(user)
		if(assayList) throw new RuntimeException("User [" + user.username + "] is owner of some Assays. Please delete those Assays first.")
		
		// delete this user
		this.deleteRole(user)
		user.delete()
		
    }
	
	def deleteRole(User user){
		def userRoles = UserRole.findAllByUser(user)
		userRoles*.delete()
	}
}
