FROM jenkins/jenkins:2.235

USER jenkins

# Plugins Install
COPY plugins.txt /usr/share/jenkins/ref/plugins.txt
RUN /usr/local/bin/install-plugins.sh < /usr/share/jenkins/ref/plugins.txt

# Auto Setup Scripts
COPY groovy/* /usr/share/jenkins/ref/init.groovy.d/
COPY resources/*.properties /var/jenkins_home/config/
COPY resources/initials/*.file /var/jenkins_home/config/initials/
COPY .security/* /var/jenkins_home/.security/
