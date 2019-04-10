declare global {
  interface PluginRegistry {
    FilePickerPlugin?: FilePickerPluginPlugin;
  }
}

export interface FilePickerPluginPlugin {
  echo(options: { value: string }): Promise<{value: string}>;
}
