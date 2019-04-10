
  Pod::Spec.new do |s|
    s.name = 'CapacitorFilepickerPlugin'
    s.version = '0.0.1'
    s.summary = 'A capacitor file picker plugin'
    s.license = 'MIT'
    s.homepage = 'https://github.com/codewaseem/capacitor-filepicker-plugin'
    s.author = 'codewaseem'
    s.source = { :git => 'https://github.com/codewaseem/capacitor-filepicker-plugin', :tag => s.version.to_s }
    s.source_files = 'ios/Plugin/Plugin/**/*.{swift,h,m,c,cc,mm,cpp}'
    s.ios.deployment_target  = '11.0'
    s.dependency 'Capacitor'
  end