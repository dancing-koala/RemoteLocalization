# Mobile translation generator

Android & iOs localization files generation based on a Google Sheet

## Requirements

This is a Kotlin project so make sure you have java correctly installed on you system.

## Installation

Unzip `RemoteLocalization-<VERSION>.zip` & put it anywhere you would install any other third party program.

## Usage

### Options

```
Usage: generate-localization-files [OPTIONS]

Options:
  --url TEXT          URL of the csv file to parse
  --target TEXT       APP or SDK
  --android-dir PATH  Target directory for Android files
  --ios-dir PATH      Target directory for iOs files
  -h, --help          Show this message and exit
```

### Examples

Generate android files for APP target directly in project directory:

```
$ /path/to/dir/RemoteLocalization/bin/RemoteLocalization --target=APP --android-dir=/path/to/android/project/app/src/main/res --url https://your.url.com/csv
 ```

Generate ios files for SDK directly in project directory:

```
$ /path/to/dir/RemoteLocalization/bin/RemoteLocalization --target=SDK --ios-dir=/path/to/ios/project/tchek/resources --url https://your.url.com/csv
```

Generate android & ios files for APP target directly in project directory:

```
$ /path/to/dir/RemoteLocalization/bin/RemoteLocalization --target=APP --android-dir=/path/to/android/project/app/src/main/res --ios-dir=/path/to/ios/project/tchek/resources --url https://your.url.com/csv
 ```


## Tips

### Launch translation generation from anywhere

Add an alias into your .bashrc or .zshrc file:

`alias update_ios_translation="/path/to/dir/RemoteLocalization/bin/RemoteLocalization --target=APP --ios-dir=/path/to/ios/project/tchek/resources" --url https://your.url.com/csv`

Then logout/login or source the .bashrc or .zshrc file:

`source .bashrc` or `source.zshrc`

You can now update the iOs translations easily:

`$ update_ios_translation`



