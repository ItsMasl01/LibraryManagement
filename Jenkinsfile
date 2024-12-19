pipeline {
    agent any
    environment {
        SONAR_PROJECT_KEY = 'Library_Management'
    }
    stages {
        stage('checkout') {
            steps {
                withCredentials([string(credentialsId: 'github-token', variable: 'GITHUB_PAT')]) {
                script {
                    if (fileExists('LibraryManagement')) {
                        dir('LibraryManagement') {
                            sh "git reset --hard" 
                            sh "git clean -fd" 
                            sh "git pull origin main"
                        }
                    } else {
                        sh "git clone https://${GITHUB_PAT}@github.com/ItsMasl01/LibraryManagement.git"
                    }
                }
                }
            }
        }
        stage('Build') {
            steps {
                sh 'mvn clean compile'
            }
        }
        stage('Test') {
            steps {
                sh 'mvn test'
            }
        }
        stage('Quality Analysis') {
            steps {
				withCredentials([string(credentialsId: 'sonarcube-token', variable: 'SONAR_TOKEN')]) {
				   
					withSonarQubeEnv('SonarQube') {
						sh """
                             mvn sonar:sonar \
                            -Dsonar.projectKey=${SONAR_PROJECT_KEY} \
                            -Dsonar.login=${SONAR_TOKEN}
                    	"""
					}	
				}
			}
        }
        stage('Deploy') {
            steps {
                echo 'Déploiement simulé réussi'
            }
        }
    }
    post {
        success {
            mail to: 'meryamassemlali@gmail.com',
                subject: 'Build Success',
                body: 'Le build a été complété avec succès.'
        }
        failure {
            mail to: 'meryamassemlali@gmail.com',
                subject: 'Build Failed',
                body: 'Le build a échoué.'
        }
    }
}
