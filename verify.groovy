#!/usr/bin/env groovy

def call() {
	def WORK_DIR = pwd();
	withEnv(["WORK_DIR=${WORK_DIR}", "PATH=/usr/local/sbin:$PATH"]) {
	withCredentials([string(credentialsId: 'github-token', variable: 'TOKEN')]) {
	script {
	  sh '''
	      cd $WORK_DIR
              sudo apt install -y nodejs
              
	      #Download required files for verification
	      mkdir -p verify
	      cd verify
	      
	      
    	      curl -o node.js https://${TOKEN}@raw.github.ibm.com/loz/HAProxy/master/node.js
	      curl -o haproxy.config https://${TOKEN}@raw.github.ibm.com/loz/HAProxy/master/haproxy.config
	      

	      echo "\n============ node.js file=================\n";
	      cat node.js
	      
	      echo "\n============ haproxy.config file=================\n";
	      cat haproxy.config
	      
	      nodejs node.js &
	      haproxy -f haproxy.config
	
	      haproxy -v | grep 1.8.13
	      
	      curl -o result1 0.0.0.0:8089
	      curl -o result2 0.0.0.0:8089
	      curl -o result3 0.0.0.0:8089
              curl -o result4 0.0.0.0:8089
	      
	      cat result1 
	      if ! grep -q "9000" result1; then
  		  echo "Not referencing port 9000 so verification failed"
		  exit 1;
	      fi
	      
	      cat result2
	      if ! grep -q "9001" result2; then
  		  echo "Not referencing port 9001 so verification failed"
		  exit 1;
	      fi
	      
	      cat result3
	      if ! grep -q "9002" result3; then
  		  echo "Not referencing port 9002 so verification failed"
		  exit 1;
	      fi
	      
	      cat result4
	      if ! grep -q "9000" result4; then
  		  echo "Not referencing port 9000 so verification failed"
		  exit 1;
	      fi
	      
             '''
	   }
	}
	}
}

return this;

