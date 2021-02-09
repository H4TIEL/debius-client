# Debius Client
Bias detection and mitigation tool for face image datasets.

![Logo](https://github.com/H4TIEL/debius-client/tree/main/src/main/resources/assets/icons/logo.png)


> Mitigate bias a face analysis model using the debius-client.
> Use the build classifier and generator container images.


### Requirements

Debius is currently extended with the following plugins.

| Requirement |
| ------ |
| Nvidia GPU |
| CUDA | 
| Docker | 
| Java | 


## Modules

### Client

Run the client on jvm

### Generator

Run the generator container image from Docker Hub.

`sudo docker run --gpus all --network host -it -p 5000:5000 h4tiel/debius-generator`

### Classifier

Run the classifier container image from Docker Hub.

`sudo docker run --gpus all --network host -it -p 5000:5000 -v "$(pwd)"/mount:/mount h4tiel/debius-classifier`


### Debug client app

`--module-path /javafx-sdk-11.0.2/lib --add-modules javafx.controls,javafx.fxml -Dagentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005`
