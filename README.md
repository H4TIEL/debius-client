# Debius 
Bias detection and mitigation tool for face image datasets.

##### Data Source
jdbc:h2:file:`$PATH`.db

### Requirements

Debius is currently extended with the following plugins.

| Requirement |
| ------ |
| Nvidia GPU |
| CUDA | 
| Docker | 
| Java | 

#### Generator

docker run --gpus all --network host -it -p 5000:5000 h4tiel/debius-generator

#### Classifier

docker run --gpus all --network host -it -p 5000:5000 -v "$(pwd)"/mount:/mount h4tiel/debius-classifier


### Debug client app

`--module-path /javafx-sdk-11.0.2/lib --add-modules javafx.controls,javafx.fxml -Dagentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005`
