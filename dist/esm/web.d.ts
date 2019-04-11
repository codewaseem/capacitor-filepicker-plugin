import { WebPlugin } from '@capacitor/core';
import { FilePickerPluginInterface } from './definitions';
export declare class FilePickerPluginWeb extends WebPlugin implements FilePickerPluginInterface {
    constructor();
    pick(): Promise<{
        uri: string;
        size: number;
        type: string;
        name: string;
        lastModified: number;
    }>;
}
declare const FilePickerPlugin: FilePickerPluginWeb;
export { FilePickerPlugin };
