# MaED
## Mobile and Embedded Development
This is a module studied during my 2nd year `BSc Computer Systems Integration` at the University of the West of England _**(UWE)**_


## System SetUp
This module is based on the development of the _OLIMEX Development Board_ **STM32F107**. Although I use a MBP this is a Linux development module and as such I have `Parallels 12` installed with a copy of `Ubuntu 14.04LTS` running.

For the module we are working with `OpenOCD`

### Installing OpenOCD
Firstly Ensure that you can use 32-bit libraries, I found this code [here](http://askubuntu.com/questions/522372/installing-32-bit-libraries-on-ubuntu-14-04-lts-64-bit)

```shell
$ sudo dpkg --add-architecture i386
$ sudo apt-get update
$ sudo apt-get install libc6:i386 libncurses5:i386 libstdc++6:i386
```
Then install a couple of needed libraries. You may be instructed to add `legacy` to the command, do so:

```shell
$ sudo apt-get install libftdi-dev libftdi1
```

Then you will need to download [OpenOCD](https://sourceforge.net/projects/openocd/files/openocd/0.9.0/) and unpack the file into your `opt/` directory. Then:

```shell
$ ./configure --enable-maintainer-mode --enable-ft2232_libftdi
```
This will fail, and ask you to edit the command with `legacy` or change it... **ADD** `legacy` to it:

```shell
$ make
$ sudo make install
```

### Installing the Compiler and Tools
I have installed the tools into `/usr/local/`, but you can install to where you wish. Simply run the `.bin` file included with this repo (ensure you have permissions set):

```shell
$ ./arm-2012.09-63-arm-none-eabi.bin
```

After this then create the following file in your `rules.d/` so that you can use the JTAG:

```shell
$ cat > /etc/udev/rules.d/99-usb-tiny.rules
SUBSYSTEMS=="usb", ATTRS{idVendor}=="15ba", ATTRS{idProduct}=="002a", MODE="0666", GROUP="plugdev"
# Use Ctrl-C to exit and save the file
```
And Finally and the following to your `.bashrc`

```shell
export PATH=$PATH:/usr/local/CodeSourcery/Sourcery_CodeBench_Lite_for_ARM_EABI/bin
```

### _**NOTE**_
This was written from memory(ish) so let me know if I have missed something out.
