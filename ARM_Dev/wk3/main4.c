/******************************************************************************
 * File Name: main4.c
 * Program Author: Daniel Hyde
 * Student ID: ********
 * Project Name: worksheets
 * Course Code: UFCFw5-30-2
 * Date Created: 20 October 2016
 *
 * Description: Building from previous files. Will turn on or off a green LED
 *              with a button press.
 *****************************************************************************/
/* Include section
 * Add all #includes here                                                    */
 #include <stm32f10x.h>
 #include <stm32f10x_rcc.h>
 #include <stm32f10x_gpio.h>
/*****************************************************************************/
/* Defines section
 * Add all #defines here                                                     */

/*****************************************************************************/
/* Function prototype section
 * Add all function prototypes here                                          */
  void delay_loop(void);
/*****************************************************************************/
GPIO_InitTypeDef GPIO_InitStructure;

int main(void)
{
	volatile uint32_t i;
	static int greenledval = 0;
  static int yellowledval = 0;
  static int wkupbtn;

  // GPIOC Periph clock enable
  RCC_APB2PeriphClockCmd(RCC_APB2Periph_GPIOC, ENABLE);
  // GPIOA Periph clock enable
  RCC_APB2PeriphClockCmd(RCC_APB2Periph_GPIOA, ENABLE);

  // Configure PC6 in output pushpull mode (green)
  GPIO_InitStructure.GPIO_Pin = GPIO_Pin_6 ;
  GPIO_InitStructure.GPIO_Speed = GPIO_Speed_50MHz;
  GPIO_InitStructure.GPIO_Mode = GPIO_Mode_Out_PP;
  GPIO_Init(GPIOC, &GPIO_InitStructure);

  // Configure PC7 in output pushpull mode (yellow)
  GPIO_InitStructure.GPIO_Pin = GPIO_Pin_7 ;
  GPIO_InitStructure.GPIO_Speed = GPIO_Speed_50MHz;
  GPIO_InitStructure.GPIO_Mode = GPIO_Mode_Out_PP;
  GPIO_Init(GPIOC, &GPIO_InitStructure);

  // Configure PA0 in input floating mode (WKUP)
  GPIO_InitStructure.GPIO_Pin = GPIO_Pin_0;
  GPIO_InitStructure.GPIO_Mode = GPIO_Mode_IN_FLOATING;
  GPIO_InitStructure.GPIO_Speed = GPIO_Speed_50MHz;
  GPIO_Init( GPIOA, &GPIO_InitStructure);

  // Configure PC13 in input floating mode (TAMPER)
  GPIO_InitStructure.GPIO_Pin = GPIO_Pin_13;
  GPIO_InitStructure.GPIO_Mode = GPIO_Mode_IN_FLOATING;
  GPIO_InitStructure.GPIO_Speed = GPIO_Speed_50MHz;
  GPIO_Init( GPIOC, &GPIO_InitStructure );

  while (1)
  {
    wkupbtn = GPIO_ReadInputDataBit(GPIOA, GPIO_Pin_0);

	  if(wkupbtn == 0)
  	{
  		GPIO_WriteBit(GPIOC, GPIO_Pin_6, Bit_SET);
  	}
  	else
  	{
  		GPIO_WriteBit(GPIOC, GPIO_Pin_6, Bit_RESET);
  	}
  }
}

#ifdef  USE_FULL_ASSERT
void assert_failed(uint8_t* file, uint32_t line)
{
    /* Infinite loop */
  while (1)
  {
  }
}
#endif
