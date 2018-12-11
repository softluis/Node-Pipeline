def afterQG(String QAResult) {
  if(QAResult == "OK"){
    slackSend color: "good", message: "${env.JOB_NAME} #${env.BUILD_NUMBER} passed SonarQube Quality Gates!"   
  }
  else if(QAResult == "ERROR") { 
    slackSend color: "danger", message: "${env.JOB_NAME} #${env.BUILD_NUMBER} didn't pass SonarQube Quality Gates!"
  }
}

def call(String buildResult) {
  if ( buildResult == "SUCCESS" ) {
    slackSend color: "good", message: "${env.JOB_NAME} #${env.BUILD_NUMBER} was successful!"
  }
  else if( buildResult == "FAILURE" ) { 
    slackSend color: "danger", message: "${env.JOB_NAME} #${env.BUILD_NUMBER} has failed"
  }
  else if( buildResult == "UNSTABLE" ) { 
    slackSend color: "warning", message: "${env.JOB_NAME} #${env.BUILD_NUMBER} was unstable"
  }
  else if( buildResult == "ABORTED" ) { 
    slackSend color: "warning", message: "${env.JOB_NAME} #${env.BUILD_NUMBER} has aborted..."
  }
  else {
    slackSend color: "danger", message: "${env.JOB_NAME} #${env.BUILD_NUMBER} result was unclear..."	
  }
}

def isRunning(String state){
	if(state == "Running"){
		slackSend color: "good", message: "${env.JOB_NAME} #${env.BUILD_NUMBER} - Your app is up and running!"
	}else{
		slackSend color: "danger", message: "${env.JOB_NAME} #${env.BUILD_NUMBER} - Your app is not running!"
	}
}

return this;
