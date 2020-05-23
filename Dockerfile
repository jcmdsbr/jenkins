FROM jenkins/jenkins:2.237
USER jenkins

COPY plugins.txt /usr/share/jenkins/ref/plugins.txt
RUN /usr/local/bin/install-plugins.sh < /usr/share/jenkins/ref/plugins.txt

COPY scripts/* /usr/share/jenkins/ref/init.groovy.d/
COPY resources/* /var/jenkins_home/config/
COPY .ssh/* /var/jenkins_home/.ssh/
