# capacitor-filepicker-plugin

A capacitor file picker plugin for android

## Installation

```
yarn add capacitor-filepicker-plugin
```

## Usage

```

handleNativeFileSelect = async () => {

        // first check plugin is installed
        if(Capacitor && Capacitor.isNative && Capacitor.Plugins.FilePicker) {
        
            // This will open the file picker dialog in your phone
            let fileInfo = await Capacitor.Plugins.FilePicker.pick(); 
            
            let fileBlob = await fetch(window.Ionic.WebView.convertFileSrc(fileInfo.uri)).then(r => r.blob());
            let ext = mime.getExtension(fileBlob.type);
            let file = new File([fileBlob],`file.${ext}`);
            
            // Now do something with the `file`, here I'm just logging it.
            console.log(file);

        } else {
            // If you are using angular, you may have to register the plugin first in your App `Init` Component.
            console.log("Filepicker plugin not installed");
        }
```


