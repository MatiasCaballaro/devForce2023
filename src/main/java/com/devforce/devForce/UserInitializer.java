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

        if (datosDePrueba) {

            // TODO faltan las relaciones. Actualizar una vez hechas las mismas para generar los objetos correctos.

            Faker faker = new Faker();

            System.out.println("------------ USUARIOS -------------");

            for (int i = 1; i < 11; i++) {
                Usuario user = new Usuario();
                user.setId(i);
                user.setNombre(faker.name().firstName());
                user.setApellido(faker.name().lastName());
                user.setUsername(user.getNombre() + user.getApellido());
                user.setEmail(user.getNombre() + user.getApellido() + "@gire.com");
                user.setPassword(user.getNombre() + "123");
                user.setPhone(faker.phoneNumber().cellPhone());
                user.setHasTeams(faker.random().nextBoolean());
                System.out.println(user);
                usuarioRepository.save(user);
            }

            System.out.println("------------ SOLICITUDES -------------");

            for (int i = 1; i < 11; i++) {
                Solicitud solicitud = new Solicitud();
                solicitud.setId(i);
                solicitud.setTipo("UDEMY");
                solicitud.setDescripcion(faker.chuckNorris().fact());
                solicitud.setEstado("PENDIENTE-MENTOR");
                solicitud.setArea("BACKEND");
                solicitud.setUsuario(usuarioRepository.findById(1l));
                System.out.println(solicitud);
                solicitudRepository.save(solicitud);
            }

            System.out.println("------------ LICENCIAS -------------");
            for (int i = 1; i < 11; i++) {
                Licencia licencia = new Licencia();
                licencia.setId(i);
                licencia.setSerie(faker.bothify("????##?###???###"));
                licencia.setEstado("DISPONIBLE");
                licencia.setPlataforma("UDEMY");
                System.out.println(licencia);
                licenciaRepository.save(licencia);
            }

            //USUARIOS DE PRUEBA
            //TEST USUARIO TIPO USER****************************************************************
            Usuario userUser = new Usuario();
            userUser.setId(11);
            userUser.setNombre("Nicolas");
            userUser.setApellido("Rivas");
            userUser.setUsername("NicolasRivas");
            userUser.setEmail((userUser.getNombre() + "." + userUser.getApellido() + "@gire.com").toLowerCase());
            userUser.setPassword("111");
            userUser.setPhone("123456789");
            userUser.setHasTeams(true);
            usuarioRepository.save(userUser);
            System.out.println(userUser.toString());
            //****************************************************************************************

            //TEST USUARIO TIPO MENTOR****************************************************************
            Usuario userMentor = new Usuario();
            userMentor.setId(12);
            userMentor.setNombre("Matias");
            userMentor.setApellido("Marchesi");
            userMentor.setUsername("MatiMarchesi");
            userMentor.setEmail((userMentor.getNombre() + "." + userMentor.getApellido() + "@gire.com").toLowerCase());
            userMentor.setPassword("111");
            userMentor.setPhone("123456789");
            userMentor.setHasTeams(true);
            usuarioRepository.save(userMentor);
            System.out.println(userMentor.toString());
            //****************************************************************************************

            //TEST USUARIO TIPO ADMIN****************************************************************
            Usuario userAdmin = new Usuario();
            userAdmin.setId(13);
            userAdmin.setNombre("Adrian");
            userAdmin.setApellido("Pierro");
            userAdmin.setUsername("AdrianPierro");
            userAdmin.setEmail((userAdmin.getNombre() + "." + userAdmin.getApellido() + "@gire.com").toLowerCase());
            userAdmin.setPassword("111");
            userAdmin.setPhone("123456789");
            userAdmin.setHasTeams(true);
            usuarioRepository.save(userAdmin);
            System.out.println(userUser.toString());
            //****************************************************************************************

            Solicitud solicitud1 = new Solicitud();
            solicitud1.setId(11);
            solicitud1.setTipo("UDEMY");
            solicitud1.setDescripcion("HOLA");
            solicitud1.setArea("BACKEND");
            solicitud1.setEstado("PENDIENTE-MENTOR");
            solicitud1.setUsuario(userUser);
            solicitudRepository.save(solicitud1);
            System.out.println(solicitud1.toString());

            Solicitud solicitud2 = new Solicitud();
            solicitud2.setId(12);
            solicitud2.setTipo("UDEMY");
            solicitud2.setDescripcion("HOLA");
            solicitud2.setArea("BACKEND");
            solicitud2.setEstado("PENDIENTE-MENTOR");
            solicitud2.setUsuario(userMentor);
            solicitudRepository.save(solicitud2);
            System.out.println(solicitud2.toString());
        }
    }
}
