
package it.fmach.metadb.services

import grails.plugin.springsecurity.SpringSecurityService;
import it.fmach.metadb.Role;
import it.fmach.metadb.UserRole;
import it.fmach.metadb.User;
import it.fmach.metadb.isatab.model.FEMAssay;
import grails.test.mixin.*

import org.junit.*

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(UserService)
@Mock([User, Role, UserRole, FEMAssay])
class UserServiceTests {

	@Test
    void testDelete() {
		// setup db
		this.setupDB()
		
        assert User.count() == 2
		assert Role.count() == 2
		assert UserRole.count() == 2
		
		def user = User.findByUsername('test')
		assert user
		
		def userService = new UserService()
		userService.delete(user)
		
		def reloadUser = User.findByUsername('test')
		assert ! reloadUser
    }
	
	
	void setupDB(){
		// override the encodePassword method if you only want to make sure the User object is saved
		User.metaClass.encodePassword = { -> }
		
		def adminRole = new Role(authority: 'ROLE_ADMIN').save(flush: true, failOnError: true)
		def userRole = new Role(authority: 'ROLE_USER').save(flush: true, failOnError: true)
  
		def adminUser = new User(username: 'admin', password: 'admin', workDir: "/admin/path")
		adminUser.save(flush: true, failOnError: true)
		
		def testUser = new User(username: 'test', password: 'test', workDir: "/user/path")
		testUser.save(flush: true, failOnError: true)
  
		UserRole.create(adminUser, adminRole, true)
		UserRole.create(testUser, userRole, true)
	
	}
}
