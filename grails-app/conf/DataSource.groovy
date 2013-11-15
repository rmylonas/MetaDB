dataSource {
    pooled = true
    dbCreate = "update"
    url = "jdbc:mysql://localhost/MetaDB"
    driverClassName = "com.mysql.jdbc.Driver"
    dialect = org.hibernate.dialect.MySQL5InnoDBDialect
    username = "root"
    password = "1234"
}

/*dataSource {
	pooled = false
	driverClassName = "org.h2.Driver"
	username = "sa"
	password = ""
}
*/

hibernate {
    cache.use_second_level_cache = true
    cache.use_query_cache = false
    cache.region.factory_class = 'net.sf.ehcache.hibernate.EhCacheRegionFactory'
}
// environment specific settings
environments {
    development {
        dataSource {
			pooled = true
			driverClassName = "org.h2.Driver"
			username = "sa"
			password = ""
            dbCreate = "create-drop" // one of 'create', 'create-drop', 'update', 'validate', ''
            url = "jdbc:h2:mem:devMetaDB"
        }
    }
    test {
        dataSource {
			pooled = true
			driverClassName = "org.h2.Driver"
			username = "sa"
			password = ""
            dbCreate = "create-drop"
            url = "jdbc:h2:mem:testMetaDB"
        }
    }
    production {
        dataSource {
            dbCreate = "create-drop"
            url = "jdbc:mysql://localhost/MetaDB"
            pooled = true
            properties {
               maxActive = -1
               minEvictableIdleTimeMillis=1800000
               timeBetweenEvictionRunsMillis=1800000
               numTestsPerEvictionRun=3
               testOnBorrow=true
               testWhileIdle=true
               testOnReturn=true
               validationQuery="SELECT 1"
            }
        }
    }
}
