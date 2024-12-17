#include <ESP8266WiFi.h>
#include <DHT.h>
#include <Firebase_ESP_Client.h>

const char* ssid = "Jey";
const char* password = "bilangsayangdulu"; 

#define FIREBASE_HOST "plantapp-f52b2-default-rtdb.firebaseio.com" 
#define FIREBASE_AUTH "A9zftumLy7KQNvuF1Q56FXfLXgtTnyASIWsk13gS"

#define SOIL_MOISTURE_PIN A0
#define DHT_PIN D2
#define DHT_TYPE DHT11

const String firebasePath = "/Iot/-OD6dujd6CdaWRoxq0j7";

FirebaseData firebaseData;
FirebaseConfig config;
FirebaseAuth auth;
DHT dht(DHT_PIN, DHT_TYPE);

void connectToWiFi() {
  WiFi.begin(ssid, password);
  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.print(".");
  }
  Serial.println("\nWi-Fi connected!");
}

void initializeFirebase() {
  config.database_url = FIREBASE_HOST;
  config.signer.tokens.legacy_token = FIREBASE_AUTH;
  config.timeout.serverResponse = 10 * 1000;
  Firebase.reconnectWiFi(true);
  Firebase.begin(&config, NULL);

  if (Firebase.ready()) {
    Serial.println("Firebase initialized successfully!");
  } else {
    Serial.println("Failed to initialize Firebase!");
  }
}

void readSensors(int &soilMoisture, float &temperature, float &humidity, float &lightIntensity) {
  soilMoisture = map(analogRead(SOIL_MOISTURE_PIN), 400, 1023, 100, 0);
  temperature = dht.readTemperature();
  humidity = dht.readHumidity();
  lightIntensity = analogRead(A0) * 0.0976;
}

void sendSensorData() {
  int soilMoisture;
  float temperature, humidity, lightIntensity;
  
  readSensors(soilMoisture, temperature, humidity, lightIntensity);

  if (isnan(temperature) || isnan(humidity)) {
    Serial.println("Failed to read from DHT sensor!");
    return;
  }

  Serial.printf("Soil Moisture: %d%%\nTemperature: %.2f°C\nHumidity: %.2f%%\nLight Intensity: %.2f lux\n",
                soilMoisture, temperature, humidity, lightIntensity);

  FirebaseJson json;
  json.add("health", String(humidity) + "%");
  json.add("light", lightIntensity);
  json.add("moist", soilMoisture);
  json.add("temp", temperature);

// send to firebase
  if (Firebase.RTDB.setJSON(&firebaseData, firebasePath.c_str(), &json)) {
    Serial.println("Data sent successfully to Firebase!");
  } else {
    Serial.printf("Failed to send data. Error: %s\n", firebaseData.errorReason().c_str());
  }
}

void setup() {
  Serial.begin(9600);
  dht.begin();

  connectToWiFi();
  initializeFirebase();
}

void loop() {
  if (WiFi.status() != WL_CONNECTED) {
    Serial.println("Wi-Fi disconnected. Reconnecting...");
    connectToWiFi();
  }

  sendSensorData();
  delay(5000);
}

