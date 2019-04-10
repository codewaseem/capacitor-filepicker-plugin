import { WebPlugin } from '@capacitor/core';
import { FilePickerPluginPlugin } from './definitions';

export class FilePickerPluginWeb extends WebPlugin implements FilePickerPluginPlugin {
  constructor() {
    super({
      name: 'FilePickerPlugin',
      platforms: ['web']
    });
  }

  async pick(): Promise<{
    uri: string,
    size: number,
    type: string,
    name: string,
    lastModified:number
  }>{
    return;
  }
}

const FilePickerPlugin = new FilePickerPluginWeb();

export { FilePickerPlugin };
