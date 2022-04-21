    #include <stdio.h>

    #include <unistd.h>

    #include "ohos_init.h"
    #include "cmsis_os2.h"

    #include <unistd.h>

    #include <at.h>
    #include <hi_at.h>

    #include "hi_wifi_api.h"


    #include "mqtt_test.h"


    void mqtt_test_thread(void * argv)
    {
        argv = argv;

        mqtt_test();

    }

    hi_u32 at_exe_mqtt_test_cmd(void)
    {
        osThreadAttr_t attr;

        attr.name = "wifi_config_thread";
        attr.attr_bits = 0U;
        attr.cb_mem = NULL;
        attr.cb_size = 0U;
        attr.stack_mem = NULL;
        attr.stack_size = 4096;
        attr.priority = 36;

        if (osThreadNew((osThreadFunc_t)mqtt_test_thread, NULL, &attr) == NULL) {
            printf("[LEDExample] Falied to create LedTask!\n");
        }

        AT_RESPONSE_OK;
        return HI_ERR_SUCCESS;
    }

    const at_cmd_func g_at_mqtt_func_tbl[] = {
        {"+MQTTTEST", 9, HI_NULL, HI_NULL, HI_NULL, (at_call_back_func)at_exe_mqtt_test_cmd},
    };

    void AtExampleEntry(void)
    {
        hi_at_register_cmd(g_at_mqtt_func_tbl, sizeof(g_at_mqtt_func_tbl)/sizeof(g_at_mqtt_func_tbl[0]));
    }

    SYS_RUN(AtExampleEntry);