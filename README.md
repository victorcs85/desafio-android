# PicPay - Desafio Android

<img src="https://github.com/mobilepicpay/desafio-android/blob/master/desafio-picpay.gif" width="300"/>

Um dos desafios de qualquer time de desenvolvimento é lidar com código legado e no PicPay isso não é diferente. Um dos objetivos de trazer os melhores desenvolvedores do Brasil é atacar o problema. Para isso, essa etapa do processo consiste numa proposta de solução para o desafio abaixo e você pode escolher a melhor forma de resolvê-lo, de acordo com sua comodidade e disponibilidade de tempo:
- Resolver o desafio previamente, e explicar sua abordagem no momento da entrevista.
- Discutir as possibilidades de solução durante a entrevista, fazendo um pair programming (bate-papo) interativo com os nossos devs.

Com o passar do tempo identificamos alguns problemas que impedem esse aplicativo de escalar e acarretam problemas de experiência do usuário. A partir disso elaboramos a seguinte lista de requisitos que devem ser cumpridos ao melhorar nossa arquitetura:

- Em mudanças de configuração o aplicativo perde o estado da tela. Gostaríamos que o mesmo fosse mantido.
- Nossos relatórios de crash têm mostrado alguns crashes relacionados a campos que não deveriam ser nulos sendo nulos e gerenciamento de lifecycle. Gostaríamos que fossem corrigidos.
- Gostaríamos de cachear os dados retornados pelo servidor.
- Haverá mudanças na lógica de negócios e gostaríamos que a arquitetura reaja bem a isso.
- Haverá mudanças na lógica de apresentação. Gostaríamos que a arquitetura reaja bem a isso.
- Com um grande número de desenvolvedores e uma quantidade grande de mudanças ocorrendo testes automatizados são essenciais.
  - Gostaríamos de ter testes unitários testando nossa lógica de apresentação, negócios e dados independentemente, visto que tanto a escrita quanto execução dos mesmos são rápidas.
  - Por outro lado, testes unitários rodam em um ambiente de execução diferenciado e são menos fiéis ao dia-a-dia de nossos usuários, então testes instrumentados também são importantes.

Boa sorte! =)

Ps.: Fique à vontade para editar o projeto inteiro, organização de pastas e módulos, bem como as dependências utilizadas


# Solução

<img src="https://github.com/victorcs85/desafio-android/blob/feature/desafio-victor/solucao_desafio_android_picpay.gif" width="300"/>

## Passos iniciais

- Atualização da versão gradle.
- Atualização das dependências.
- Namespace adicionado no gradle.
- Removido o kotlin extensions -> usado o ViewBinding.
- Removido gson, dagger, rxjava e security network.
- Adicionado exported no manifest.
- Adicionado nos repositories mavenCentral e gradlePluginPortal.
- Removido o jcenter (não existe mais).
- Renomeado PicPayService -> UserRepository
- No layout da MainActivity simplificado layout para evitar o aninhamento de views.

## Premissas
- Em mudanças de configuração o aplicativo perde o estado da tela. Gostaríamos que o mesmo fosse mantido.
  [x] Implementado ViewModel para manter o estado da tela.
- 
- Nossos relatórios de crash têm mostrado alguns crashes relacionados a campos que não deveriam ser nulos sendo nulos e gerenciamento de lifecycle. Gostaríamos que fossem corrigidos.
    [x] Implementado LiveData para observar o ciclo de vida dos dados.

- Gostaríamos de cachear os dados retornados pelo servidor.
    [x] Implementado cache com Room.

- Haverá mudanças na lógica de negócios e gostaríamos que a arquitetura reaja bem a isso.
    [x] Implementado Repository Pattern. 
    [x] Implementado Clean Architecture em 3 camadas (data, domain e presentation).

- Haverá mudanças na lógica de apresentação. Gostaríamos que a arquitetura reaja bem a isso.
    [x] Implementado MVVM.
    [x] Implementado ViewBinding.
    [x] Implementado LiveData.

- Com um grande número de desenvolvedores e uma quantidade grande de mudanças ocorrendo testes automatizados são essenciais.
  - Gostaríamos de ter testes unitários testando nossa lógica de apresentação, negócios e dados independentemente, visto que tanto a escrita quanto execução dos mesmos são rápidas.
    [x] Implementado testes unitários em repositories e viewmodel.
  - Por outro lado, testes unitários rodam em um ambiente de execução diferenciado e são menos fiéis ao dia-a-dia de nossos usuários, então testes instrumentados também são importantes.
    [x] Implementado testes instrumentados na MainActivity.
