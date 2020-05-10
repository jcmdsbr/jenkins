FROM jenkins/jenkins:2.235

USER jenkins

# Plugins Install
COPY plugins.txt /usr/share/jenkins/ref/plugins.txt
RUN /usr/local/bin/install-plugins.sh < /usr/share/jenkins/ref/plugins.txt

# Auto Setup Scripts
COPY scripts/* /usr/share/jenkins/ref/init.groovy.d/
COPY resources/* /var/jenkins_home/config/
COPY .ssh/* /var/jenkins_home/.ssh/
