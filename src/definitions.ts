declare global {
  interface PluginRegistry {
    FilePickerPlugin?: FilePickerPluginPlugin;
  }
}

export interface FilePickerPluginPlugin {
  pick(): Promise<{
    uri: string,
    file_path: string,
    file_data: {
      bits: Blob,
      size: number,
      type: string,
      name: string,
      lastModified:number
    }
  }>;
}
