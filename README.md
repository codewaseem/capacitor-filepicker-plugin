# capacitor-filepicker-plugin

A capacitor file picker plugin for android

## Installation

```
yarn add capacitor-filepicker-plugin
```

## Usage

To pick a file from the storage, do the following,

```
let fileInfo = await Capacitor.Plugins.FilePicker.pick();
```

The `fileInfo` will have a `uri` property which points to the location of the file in your android phone.
The URI is in `file:android.net.Uri` format. You may need to convert it into normal File URI format using
this `(window.Ionic.WebView.convertFileSrc(fileInfo.uri)`. See the example below for more details.


## Example

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
}
```


