stage 'build_Project'
node{
echo 'Building..'
  if(isUnix()){
  echo 'second Building..'
  sh 'gradlew build --info'

  }
  else{
    bat 'gradle build --info'
  }
}