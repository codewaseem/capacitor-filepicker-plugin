import { WebPlugin } from '@capacitor/core';
import { FilePickerPluginPlugin } from './definitions';
export declare class FilePickerPluginWeb extends WebPlugin implements FilePickerPluginPlugin {
    constructor();
    pick(): Promise<{
        uri: string;
        file_path: string;
        file_data: {
            bits: Blob;
            size: number;
            type: string;
            name: string;
            lastModified: number;
        };
    }>;
}
declare const FilePickerPlugin: FilePickerPluginWeb;
export { FilePickerPlugin };
