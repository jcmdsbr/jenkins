
import java.lang.System
import jenkins.model.*
import hudson.security.*
import hudson.model.*

def HOME = System.getenv('JENKINS_HOME')
def properties = new ConfigSlurper().parse(new File("$HOME/config/authentication.properties").toURI().toURL())

if (properties.owndb.enabled) {
    def realm = new HudsonPrivateSecurityRealm(false)
    def strategy = new GlobalMatrixAuthorizationStrategy()
    
    properties.owndb.users.each { key, value ->
        def passwordFile = new File(value.path)
        realm.createAccount(value.userId, passwordFile.text.trim())
        if (value.isAdmin == true) {
            strategy.add(Jenkins.ADMINISTER, value.userId)
        } else {
            strategy.add(Jenkins.READ, value.userId)
        }
    }

    Jenkins.instance.setAuthorizationStrategy(strategy)
    Jenkins.instance.setSecurityRealm(realm)
    Jenkins.instance.save()

    def dslSecurity = Jenkins.instance.getDescriptor('javaposse.jobdsl.plugin.GlobalJobDslSecurityConfiguration')

    if (dslSecurity != null) {
        dslSecurity.setUseScriptSecurity(false)
        Jenkins.instance.save()
    }
}
