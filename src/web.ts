import { WebPlugin } from '@capacitor/core';
import { FilePickerPluginInterface } from './definitions';

export class FilePickerPluginWeb extends WebPlugin implements FilePickerPluginInterface {
  constructor() {
    super({
      name: 'FilePickerPlugin',
      platforms: ['web']
    });
  }

  async pick(): Promise<{
    uri: string
  }>{
    return;
  }
}

const FilePickerPlugin = new FilePickerPluginWeb();

export { FilePickerPlugin };
