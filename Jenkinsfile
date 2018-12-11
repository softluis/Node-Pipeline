pipeline{
	environment{
		scannerHome = tool 'Scanner';
		slackMet = load("slackNotifications.groovy");
	}

	agent any
	
	stages{
	  stage('SCM') {
		steps{
			git 'https://github.com/ricardo-softinsa/Node-Pipeline.git'
		}
	  }
	  stage('SonarQube analysis') {
		steps{
			echo "SonarQube analysis"
			withSonarQubeEnv('SonarServer') {
			  bat "\"${scannerHome}/bin/sonar-scanner\""
			}
		}
	  }
	  stage("SonarQube Quality Gate") { 
		steps{
			echo "SonaQube Quality Gate"
		    timeout(time: 2, unit: 'MINUTES') {  
				waitForQualityGate abortPipeline: true
		    }
			slackSend color: "good", message: "${env.JOB_NAME} #${env.BUILD_NUMBER} has passed the Quality Gates!"
		}
	  }
	  stage("Pushing to Cloud"){
		steps{
			echo "Pushing into the cloud...";
			cfPush(
				target: 'api.eu-gb.bluemix.net',
				organization: 'ricardo.miguel.magalhaes@pt.softinsa.com',
				cloudSpace: 'dev',
				credentialsId: 'CFPush',
			)
		}
	  }
	  stage("Check App Status"){
		steps{
			echo "Checking if the App is live..."
			script{
				try{
					bat "curl -s --head  --request GET https://node-softinsa-app.eu-gb.mybluemix.net/ | grep '200 OK'"
					echo "The app is up and running!"
					slackSend color: "good", message: "${env.JOB_NAME} #${env.BUILD_NUMBER} - Your app is up and running!"
				}catch(e){
					echo "The app is down..."
					slackSend color: "danger", message: "${env.JOB_NAME} #${env.BUILD_NUMBER} - Your app is down..."
				}
			}
		}
	  }
	}
	post{
		always{
			echo "This executes always..."
		}
		success{
			slackSend color: "good", message: "${env.JOB_NAME} #${env.BUILD_NUMBER} was successful!"
		}
		unstable{
			slackSend color: "purple", message: "${env.JOB_NAME} #${env.BUILD_NUMBER} is unstable!"
		}
		failure{
			slackSend color: "danger", message: "${env.JOB_NAME} #${env.BUILD_NUMBER} has failed..."
		}
		aborted{
			slackSend color: "danger", message: "${env.JOB_NAME} #${env.BUILD_NUMBER} has aborted..."
		}
		changed{
			slackSend color: "orange", message: "${env.JOB_NAME} #${env.BUILD_NUMBER} has changed since last build."
		}
	}
}