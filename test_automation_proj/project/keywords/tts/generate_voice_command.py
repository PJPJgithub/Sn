import pyttsx3
import os
import sys
sys.path.append(os.environ.get('AUTOMATION_HOME'))

from robot.api.deco import keyword

@keyword("Generate Voice Command")
def generate_voice_command(text,rate):#, output_dir
    try:
        engine = pyttsx3.init()
        #output_path = os.path.join(output_dir, 'voice_command.mp3')
        #print(f"Generating voice command for text: {text}")
        #print(f"Saving to: {output_path}")
        #engine.save_to_file(text, output_path)
        engine.setProperty('rate', rate)     # Set desired speech rate (default is usually around 200)
        engine.say(text)
        engine.runAndWait()
        print("Voice command generated successfully.")
    except Exception as e:
        print(f"Error generating voice command: {e}")