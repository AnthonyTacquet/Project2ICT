#include <NewPing.h>
#include <DFPlayerMini_Fast.h>
#include <Servo.h>

#define TRIGGER_PIN  5  // Arduino pin tied to trigger pin on the ultrasonic sensor.
#define ECHO_PIN     6  // Arduino pin tied to echo pin on the ultrasonic sensor.
#define MAX_DISTANCE 200 // Maximum distance we want to ping for (in centimeters). Maximum sensor distance is rated at 400-500cm.
#define ledPin       7        
#define button       2

// Motor A connections
#define in1          10
#define in2          11
// Motor B connections
#define in3          12
#define in4          13

#define servoPin     3

enum direction {
  FORWARDS,
  BACKWARDS,
  OFF 
};

int steeringRotation = 90;
int songcount = 3;
String state = "";
bool active = false;

NewPing sonar(TRIGGER_PIN, ECHO_PIN, MAX_DISTANCE); // NewPing setup of pins and maximum distance.
Servo servo;  // create servo object to control a servo

DFPlayerMini_Fast myDFPlayer;

void setServo(int grad){
  if (grad > 180)
    grad = 180;
  else if (grad < 0)
    grad = 0;

  servo.write(grad);
}

void setup() {
  // Set all the motor control pins to outputs
	pinMode(in1, OUTPUT);
	pinMode(in2, OUTPUT);
	pinMode(in3, OUTPUT);
	pinMode(in4, OUTPUT);
	
	// Turn off motors - Initial state
	digitalWrite(in1, LOW);
	digitalWrite(in2, LOW);
	digitalWrite(in3, LOW);
	digitalWrite(in4, LOW);

  pinMode(ledPin, OUTPUT);
  pinMode(button, INPUT_PULLUP);
  digitalWrite(ledPin, LOW);

  Serial.begin(115200); // Open serial monitor at 115200 baud to see ping results.
  Serial1.begin(9600); // Serial 1 for bluetooth
  Serial2.begin(9600); // Serial 2 fro sd card

  attachInterrupt(digitalPinToInterrupt(button), stopSequence, RISING);

  servo.attach(servoPin);
  setServo(90);

  if (!myDFPlayer.begin(Serial2)) {  //Use softwareSerial to communicate with mp3.
    Serial.println(F("Unable to begin:"));
    Serial.println(F("1.Please recheck the connection!"));
    Serial.println(F("2.Please insert the SD card!"));
    while(true);
  }
  Serial.println(F("DFPlayer Mini online."));
  
  myDFPlayer.volume(20);  //Set volume value. From 0 to 30 
}

void playSong(){
  int songNumber = random(1, songcount + 1); // Choose random song
  Serial1.println(songNumber);
  myDFPlayer.play(songNumber);  //Play the first mp3
  myDFPlayer.volume(20);  //Set volume value. From 0 to 30 
  delay(500);
  myDFPlayer.resume();
}

void stopSong(){
  myDFPlayer.stop();
  delay(500);
}

void setDirection(direction dir){
  if (dir == FORWARDS){
    digitalWrite(in1, HIGH);
    digitalWrite(in2, LOW);
    digitalWrite(in3, HIGH);
    digitalWrite(in4, LOW);
  }
  else if (dir == BACKWARDS){
    digitalWrite(in1, LOW);
	  digitalWrite(in2, HIGH);
	  digitalWrite(in3, LOW);
	  digitalWrite(in4, HIGH);
  } else if (dir == OFF){
    digitalWrite(in1, LOW);
	  digitalWrite(in2, LOW);
	  digitalWrite(in3, LOW);
	  digitalWrite(in4, LOW);
  }
}

void stopSequence(){
  digitalWrite(ledPin, LOW); // Turn LED OFF
  stopSong();
  Serial1.println("Stopping sequence ...."); // Send back, to the phone, the String "LED: ON"
  active = false;
  setDirection(OFF);
} 

void startSequence(){
  digitalWrite(ledPin, HIGH); // Turn LED OFF
  Serial1.println("Starting sequence ...."); // Send back, to the phone, the String "LED: ON"
  playSong();
  state = "";
  active = true;
  setDirection(FORWARDS);
}

void loop() {
  if(Serial1.available() > 0){ // Checks whether data is comming from the serial port
    state = Serial1.readString(); // Reads the data from the serial port
    state.trim();
    Serial1.println(state); // Send back, to the phone, the String "LED: ON"
    Serial.println(state);
  }
  if (state == "START-SEQUENCE")
    startSequence();

  if (active == true){
    unsigned int uS = sonar.ping();
    int distance = sonar.convert_cm(uS);
    // Serial.println(distance);

    if (distance < 10){
      setDirection(BACKWARDS);
      setServo(0);
    }
    else if (distance > 50){
      setDirection(FORWARDS);
      setServo(90);
    } 
  }
  state = "";
  delay(50);
}
