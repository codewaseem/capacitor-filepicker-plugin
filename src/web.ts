import { WebPlugin } from '@capacitor/core';
import { FilePickerPluginPlugin } from './definitions';

export class FilePickerPluginWeb extends WebPlugin implements FilePickerPluginPlugin {
  constructor() {
    super({
      name: 'FilePickerPlugin',
      platforms: ['web']
    });
  }

  async echo(options: { value: string }): Promise<{value: string}> {
    console.log('ECHO', options);
    return options;
  }
}

const FilePickerPlugin = new FilePickerPluginWeb();

export { FilePickerPlugin };
