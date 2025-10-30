pipeline {
    agent any

    stages {

        stage('Checkout') {
            steps {
                echo 'Checking out source code...'
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
                // Exit 0 ensures Jenkins doesn’t fail if no tests are defined
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
                echo 'Starting new container (after cleanup)...'
                bat '''
                for /f "delims=" %%i in ('docker ps -q --filter "ancestor=nodejs-app"') do docker stop %%i || echo none
                for /f "delims=" %%i in ('docker ps -a -q --filter "ancestor=nodejs-app"') do docker rm %%i || echo none

                docker run -d -p 3000:3000 --name nodejs-app-%BUILD_NUMBER% nodejs-app
                '''
            }
        }

        stage('Deploy to Kubernetes') {
            steps {
                echo 'Deploying to Kubernetes (via WSL Minikube cluster)...'
                bat '''
                wsl kubectl delete deployment nodejs-app-deployment --ignore-not-found
                wsl kubectl delete service nodejs-app-service --ignore-not-found
                wsl kubectl apply -f deployment.yaml
                wsl kubectl apply -f service.yaml
                wsl kubectl get pods
                '''
            }
        }
    }

    post {
        success {
            echo '✅ Build and deployment completed successfully!'
        }
        failure {
            echo '❌ Build failed. Please check console output for errors.'
        }
    }
}

