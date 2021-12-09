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
  --url               URL of the csv file to parse
  --android           Generate localization files for Android
  --ios               Generate localization files for iOs
  --android-dir PATH  Target directory for Android files
  --ios-dir PATH      Target directory for iOs files
  -h, --help          Show this message and exit

```

### Examples

Generate android files in current working directory:

`$ /path/to/dir/RemoteLocalization/bin/RemoteLocalization --android --url https://your.url.com/csv`

Generate android files directly in project directory:

`$ /path/to/dir/RemoteLocalization/bin/RemoteLocalization --android --android-dir=/path/to/android/project/app/src/main/res --url https://your.url.com/csv`


Generate ios files in current working directory:

`$ /path/to/dir/RemoteLocalization/bin/RemoteLocalization --ios --url https://your.url.com/csv`

Generate ios files directly in project directory:

`$ /path/to/dir/RemoteLocalization/bin/RemoteLocalization --ios --ios-dir=/path/to/ios/project/tchek/resources --url https://your.url.com/csv`

## Tips

### Launch translation generation from anywhere

Add an alias into your .bashrc or .zshrc file:

`alias update_ios_translation="/path/to/dir/RemoteLocalization/bin/RemoteLocalization --ios --ios-dir=/path/to/ios/project/tchek/resources" --url https://your.url.com/csv`

Then logout/login or source the .bashrc or .zshrc file:

`source .bashrc` or `source.zshrc`

You can now update the iOs translations easily:

`$ update_ios_translation`



