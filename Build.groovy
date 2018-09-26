#!/usr/bin/env groovy

def call() {
	def WORK_DIR = pwd();
	withEnv(["WORK_DIR=${WORK_DIR}"]) {
	script {
	sh '''
          cd $WORK_DIR
          wget http://www.haproxy.org/download/1.8/src/haproxy-1.8.13.tar.gz
          tar xzvf haproxy-1.8.13.tar.gz
          cd haproxy-1.8.13

          make TARGET=linux26
          sudo make install

	  '''
	  }
	}
}

return this;

