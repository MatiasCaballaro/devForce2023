package com.devforce.devForce;

import com.devforce.devForce.model.entity.Licencia;
import com.devforce.devForce.model.entity.Solicitud;
import com.devforce.devForce.model.entity.Usuario;
import com.devforce.devForce.repository.LicenciaRepository;
import com.devforce.devForce.repository.SolicitudRepository;
import com.devforce.devForce.repository.UsuarioRepository;
import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class UserInitializer implements CommandLineRunner {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    SolicitudRepository solicitudRepository;

    @Autowired
    LicenciaRepository licenciaRepository;

    @Value("${sample.data}")
    private Boolean datosDePrueba;

    @Override
    public void run(String... args) throws Exception {

        if(datosDePrueba){

            Faker faker = new Faker();

            System.out.println("------------ USUARIOS -------------");

            for(int i = 1; i < 11; i++) {
                Usuario user = new Usuario();
                user.setId(i);
                user.setNombre(faker.name().firstName());
                user.setApellido(faker.name().lastName());
                user.setUsername(user.getNombre() + user.getApellido());
                user.setEmail(user.getNombre() + user.getApellido() + "@gire.com");
                user.setPassword(user.getNombre()+"123");
                user.setPhone(faker.phoneNumber().cellPhone());
                user.setHasTeams(faker.random().nextBoolean());
                System.out.println(user);
                usuarioRepository.save(user);
            }

            // TODO Generar usuarios tipo Mentor y Admin
            /*Usuario usuarioPrueba = new Usuario();
            usuarioPrueba.setId(1);
            usuarioPrueba.setNombre("Pirulo");
            usuarioPrueba.setApellido("Rivarola");
            usuarioPrueba.setUsername("PiruloRivarola");
            usuarioPrueba.setEmail(usuarioPrueba.getNombre() + usuarioPrueba.getApellido() + "@gire.com");
            usuarioPrueba.setPassword("Pirulo123");
            usuarioPrueba.setPhone("0303456");
            usuarioPrueba.setHasTeams(true);
            System.out.println(usuarioPrueba);
            usuarioRepository.save(usuarioPrueba);*/

            System.out.println("------------ SOLICITUDES -------------");

            for(int i = 1; i < 11; i++) {
                Solicitud solicitud = new Solicitud();
                solicitud.setId(i);
                solicitud.setTipo("UDEMY");
                solicitud.setDescripcion(faker.chuckNorris().fact());
                solicitud.setEstado("PENDIENTE-MENTOR");
                solicitud.setArea("BACKEND");
                System.out.println(solicitud);
                solicitudRepository.save(solicitud);
            }


            System.out.println("------------ LICENCIAS -------------");
            for(int i = 1; i < 11; i++) {
                Licencia licencia = new Licencia();
                licencia.setId(i);
                licencia.setSerie(faker.bothify("????##?###???###"));
                licencia.setEstado("DISPONIBLE");
                licencia.setPlataforma("UDEMY");
                System.out.println(licencia);
                licenciaRepository.save(licencia);
            }


        }
    }
}

