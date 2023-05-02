# Set the JAVA_HOME environment variable
export JAVA_HOME="/Library/Java/JavaVirtualMachines/adoptopenjdk-8.jdk/Contents/Home"
export PATH=$JAVA_HOME/bin:$PATH

# Run the application
mvn spring-boot:run
