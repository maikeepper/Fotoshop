grails.plugin.springsecurity.logout.postOnly = false

// Added by the Spring Security Core plugin:
grails.plugin.springsecurity.userLookup.userDomainClassName = 'shop.fotos.authentication.User'
grails.plugin.springsecurity.userLookup.authorityJoinClassName = 'shop.fotos.authentication.UserRole'
grails.plugin.springsecurity.authority.className = 'shop.fotos.authentication.Role'
grails.plugin.springsecurity.controllerAnnotations.staticRules = [
	[pattern: '/',               access: ['permitAll']],
	[pattern: '/error',          access: ['permitAll']],
	[pattern: '/notFound',       access: ['permitAll']],
	[pattern: '/h2-console',     access: ['ROLE_ADMIN']],
	[pattern: '/dbconsole',      access: ['ROLE_ADMIN']],
	[pattern: '/h2-console/**',  access: ['ROLE_ADMIN']],
	[pattern: '/dbconsole/**',   access: ['ROLE_ADMIN']],
	[pattern: '/index',          access: ['permitAll']],
	[pattern: '/index.gsp',      access: ['permitAll']],
	[pattern: '/shutdown',       access: ['permitAll']],
	[pattern: '/assets/**',      access: ['permitAll']],
	[pattern: '/**/js/**',       access: ['permitAll']],
	[pattern: '/**/css/**',      access: ['permitAll']],
	[pattern: '/**/images/**',   access: ['permitAll']],
	[pattern: '/**/favicon.ico', access: ['permitAll']]
]

grails.plugin.springsecurity.filterChain.chainMap = [
	[pattern: '/assets/**',      filters: 'none'],
	[pattern: '/**/js/**',       filters: 'none'],
	[pattern: '/**/css/**',      filters: 'none'],
	[pattern: '/**/images/**',   filters: 'none'],
	[pattern: '/**/favicon.ico', filters: 'none'],
	[pattern: '/**',             filters: 'JOINED_FILTERS']
]

