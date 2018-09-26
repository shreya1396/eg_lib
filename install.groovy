#!/usr/bin/env groovy

def call() {
	script {
			  sh '''
			  	source /home/test/.bashrc
			  	sudo apt-get -y install wget tar make gcc curl
			   '''
		   }
}

return this;

