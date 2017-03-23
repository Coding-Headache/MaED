# Worksheet 3
## Programming GPIO

When this file was originally downloaded from my UWE student pages the file `main.c.bak` was the only `main` file. It will flash the green LEDon the board on and off. As I have worked through our sheets I relabel and copy that which I need for the next task.

## Output

### Task 1 (main1.c)

This task was to evolve the original file to include a delay loop within the code.

### Task 2 (main2.c)

Replace `GPIO_WriteBit()` function with

```C
// Set PC6 turn on
GPIOC->BSRR = 1 << 6;
//...wait

// Reset PC6 turn off
GPIOC->BRR = 1 << 6;
```

Test out that this is true by replacing the calls with this code. Using the STM32 programmers manual explain to the lab tutor how this is working. Hint read the section on GPIO. Build the library and non-library version and compare how large they are using arm-none-eab-size command. Why are they different?

### Task 3 (main3.c)

Produce code that will alternately flash between the green and yellow LEDs.

## Input

### Task 1 (main4.c)

Write a simple program that reads the WKUP button and turns on and off the green LED each time it is pressed.

### Task 2 (main5.c)

Write a program which toggles between the yellow and green LEDs when a button is pressed.

## Credit Task (main.c)

All of the previous tasks have been so that we are able to get a handle on input and output handling. For us to receive any credits for this worksheet we must combine what we have learned.....

Design a finite state machine and code to de-bounce the switch input.


## Working with these files:

If you wish to try the code to see wha it does then rename the desired file to `main.c` then, whilst within the directory in your terminal type:

```shell
$ make
```
This will produce numerous files, including `demo.elf` which is your executable.

Once testing is complete then go back to the terminal and type:

```shell
$ make clean
```

Which will tidy up the directory again.
