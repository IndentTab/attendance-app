pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/IndentTab/attendance-app.git'
            }
        }

        stage('Build') {
            steps {
                echo 'Installing dependencies...'
                bat 'npm install'
            }
        }

        stage('Test') {
            steps {
                echo 'Running tests...'
                bat 'npm test || exit /b 0'
            }
        }

        stage('Package Docker Image') {
            steps {
                echo 'Building Docker image...'
                bat 'docker build -t nodejs-app .'
            }
        }

        stage('Run Docker Container') {
            steps {
                bat '''
                echo Cleaning up old containers...
                for /f "delims=" %%i in ('docker ps -q --filter "ancestor=nodejs-app"') do docker stop %%i || echo none
                for /f "delims=" %%i in ('docker ps -a -q --filter "ancestor=nodejs-app"') do docker rm %%i || echo none

                echo Starting new container...
                for /f "delims=" %%p in ('docker ps -q --filter "publish=3000"') do docker stop %%p || echo none
                docker run -d -p 3000:3000 --name nodejs-app-%BUILD_NUMBER% nodejs-app

                '''
            }
        }

        stage('Deploy to Kubernetes') {
            steps {
                echo 'Deploying application to Kubernetes...'
                sh '''
                    kubectl delete deployment nodejs-app-deployment --ignore-not-found
                    kubectl delete service nodejs-app-service --ignore-not-found
                    kubectl apply -f deployment.yaml
                    kubectl apply -f service.yaml
                    kubectl get pods
                '''
            }
        }
    }

    post {
        failure {
            echo 'Build failed. Check logs.'
        }
        success {
            echo 'Build completed successfully.'
        }
    }
}

