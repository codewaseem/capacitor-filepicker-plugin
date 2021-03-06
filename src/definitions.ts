declare global {
  interface PluginRegistry {
    FilePickerPlugin?: FilePickerPluginInterface;
  }
}

export interface FilePickerPluginInterface {
  pick(): Promise<{
      uri: string
    }>;
}
