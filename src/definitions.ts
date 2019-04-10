declare global {
  interface PluginRegistry {
    FilePickerPlugin?: FilePickerPluginPlugin;
  }
}

export interface FilePickerPluginPlugin {
  pick(): Promise<{
      uri: string,
      size: number,
      type: string,
      name: string,
      lastModified:number
    }>;
}
