/******************************************************************************
 * File Name: main3.c
 * Program Author: Daniel Hyde
 * Student ID: ********
 * Project Name: worksheets
 * Course Code: UFCFw5-30-2
 * Date Created: 20 October 2016
 *
 * Description: Building from previous files. Will flash a green LED and a
 *              yellow LED alternately.
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
  static int yellowledval = 1;

  /* GPIOC Periph clock enable */
  RCC_APB2PeriphClockCmd(RCC_APB2Periph_GPIOC, ENABLE);

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

  while (1)
  {
		GPIOC->BSRR = 1 /*greenledval*/ << 6;
    GPIOC->BRR = 1 /*yellowledval*/ << 7;
    greenledval = 1 - greenledval;
    yellowledval = 1 - yellowledval;
    delay_loop();
		GPIOC->BRR = 1 /*greenledval*/ << 6;
    GPIOC->BSRR = 1 /*yellowledval*/ << 7;
		greenledval = 1 - greenledval;
    yellowledval = 1 - yellowledval;
    delay_loop();
  }
}
/******************************************************************************
 * Function Name      :  delay_loop
 *    returns         :  Void
 *    arg1            :  Void
 *    arg2            :  Void
 * Created by         :  Daniel Hyde
 * Date created       :  20 October 2016
 * Description        :  Simple delay loop.
 * Notes              :  Nil
 *****************************************************************************/
void delay_loop (void)
{
    int j = 0;
    for (j = 0;j<1000000;j++)
    {
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
exit
