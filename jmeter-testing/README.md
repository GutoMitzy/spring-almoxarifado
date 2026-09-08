Execução de testes (todos a partir do path jmeter-testing):

ConsultaItens: 
    GUI: apache-jmeter-5.6.3/bin/jmeter.bat -JADMIN_USERNAME= -JADMIN_PASSWORD= -t tests/ConsultaItens.jmx
    CMD: apache-jmeter-5.6.3/bin/jmeter -n -JADMIN_USERNAME= -JADMIN_PASSWORD= \
        -t tests/ConsultaItens.jmx \
        -l results/ConsultaItensResults$(date +%Y%m%d-%H%M%S).jtl \
        -e -o reports/load-test$(date +%Y%m%d-%H%M%S) \
        -Jjmeter.reportgenerator.overall_granularity=1000