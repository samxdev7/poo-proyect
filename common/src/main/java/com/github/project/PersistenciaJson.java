package com.github.project;

import com.codename1.io.FileSystemStorage;
import com.codename1.io.JSONParser;
import com.codename1.io.Util;
import com.codename1.processing.Result;
import com.github.project.model.Conductor;
import com.github.project.model.Cooperativa;
import com.github.project.model.GestorCooperativa;
import com.github.project.model.Jornada;
import com.github.project.model.Monitor;
import com.github.project.model.PuntoControl;
import com.github.project.model.Ruta;
import com.github.project.model.Usuario;
import com.github.project.model.Vehiculo;
import com.github.project.model.VehiculoDiesel;
import com.github.project.model.VehiculoElectrico;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PersistenciaJson {

    private static final String ARCHIVO_JSON = FileSystemStorage.getInstance().getAppHomePath() + "cooperativa.json";

    public static void guardarDatosJson(Cooperativa c) {
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("ruc", c.getNumeroDeRegistro());
            map.put("nombre", c.getNombre());
            map.put("direccion", c.getDireccion());
            
            // Flotas
            List<Map<String, Object>> flotasList = new ArrayList<>();
            for (Vehiculo v : c.getFlotas()) {
                Map<String, Object> vm = new HashMap<>();
                vm.put("placa", v.getPlaca());
                vm.put("marca", v.getMarca());
                vm.put("modelo", v.getModelo());
                vm.put("tipoCombustible", v.getTipoCombustible());
                vm.put("estadoDeVehiculo", v.getEstadoDeVehiculo());
                if (v instanceof VehiculoElectrico) {
                    VehiculoElectrico ve = (VehiculoElectrico) v;
                    vm.put("idVehiculoElectrico", ve.getIdVehiculoElectrico());
                    vm.put("capacidadBateria", ve.getCapacidadBateria());
                    vm.put("nivelCarga", ve.getNivelCarga());
                    vm.put("ciclosDeCarga", ve.getCiclosDeCarga());
                } else if (v instanceof VehiculoDiesel) {
                    VehiculoDiesel vd = (VehiculoDiesel) v;
                    vm.put("idVehiculoDiesel", vd.getIdVehiculoDiesel());
                    vm.put("capacidadDeTanque", vd.getCapacidadDeTanque());
                    vm.put("nivelDeCombustible", vd.getNivelDeCombustible());
                }
                flotasList.add(vm);
            }
            map.put("flotas", flotasList);

            // Usuarios
            List<Map<String, Object>> usuariosList = new ArrayList<>();
            for (Usuario u : c.getUsuarios()) {
                Map<String, Object> um = new HashMap<>();
                um.put("idUsuario", u.getIdUsuario());
                um.put("nombre", u.getNombre());
                um.put("contrasena", u.getContrasena());
                um.put("tipoUsuario", u.getTipoUsuario());
                um.put("isActivo", u.isActivo());
                if (u instanceof Conductor) {
                    um.put("licencia", ((Conductor) u).getLicenciaDeConducir());
                } else if (u instanceof Monitor) {
                    um.put("idMonitor", ((Monitor) u).getIdMonitor());
                } else if (u instanceof GestorCooperativa) {
                    um.put("idGestor", ((GestorCooperativa) u).getIdGestor());
                }
                usuariosList.add(um);
            }
            map.put("usuarios", usuariosList);

            // Rutas
            List<Map<String, Object>> rutasList = new ArrayList<>();
            for (Ruta r : c.getRutas()) {
                Map<String, Object> rm = new HashMap<>();
                rm.put("idRuta", r.getIdRuta());
                rm.put("nombreDeRuta", r.getNombreDeRuta());
                rm.put("puntoDeSalida", r.getPuntoDeSalida());
                rm.put("puntoDeLlegada", r.getPuntoDeLlegada());
                rm.put("horaDeSalida", r.getHoraDeSalida());
                rm.put("horaDeLlegada", r.getHoraDeLlegada());
                rm.put("duracion", r.getDuracionEstimadaMinutos());
                rm.put("distancia", r.getDistanciaTotalKm());
                
                List<Map<String, Object>> ptList = new ArrayList<>();
                for (PuntoControl pc : r.getPuntosDeControl()) {
                    Map<String, Object> pcm = new HashMap<>();
                    pcm.put("idControl", pc.getIdControl());
                    pcm.put("ubicacion", pc.getUbicacion());
                    pcm.put("horaProgramada", pc.getHoraProgramada());
                    pcm.put("horaRealDePaso", pc.getHoraRealDePaso());
                    pcm.put("superado", pc.isSuperado());
                    if (pc.getMonitorACargo() != null) {
                        pcm.put("idMonitorControl", pc.getMonitorACargo().getIdUsuario());
                    }
                    ptList.add(pcm);
                }
                rm.put("puntosDeControl", ptList);
                rutasList.add(rm);
            }
            map.put("rutas", rutasList);

            map.put("jornadasActivas", serializarJornadas(c.getJornadasActivas()));
            map.put("jornadasFinalizadas", serializarJornadas(c.getJornadasFinalizadas()));

            String jsonStr = Result.fromContent(map).toString();
            try (OutputStream os = FileSystemStorage.getInstance().openOutputStream(ARCHIVO_JSON);
                 OutputStreamWriter out = new OutputStreamWriter(os, "UTF-8")) {
                out.write(jsonStr);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static List<Map<String, Object>> serializarJornadas(List<Jornada> listas) {
        List<Map<String, Object>> res = new ArrayList<>();
        for (Jornada j : listas) {
            Map<String, Object> jm = new HashMap<>();
            jm.put("numero", j.getNumeroDeJornada());
            jm.put("fecha", j.getFecha());
            jm.put("horaInicio", j.getHoraDeInicioRegistrado());
            jm.put("horaFin", j.getHoraDeFinRegistrado());
            jm.put("estado", j.getEstadoDeJornada());
            jm.put("km", j.getKilometrosRecorridos());
            jm.put("pasajeros", j.getPasajerosTransportados());
            jm.put("viajes", j.getCantidadDeViajes());
            jm.put("idVehiculo", j.getVehiculo() != null ? j.getVehiculo().getPlaca() : "");
            jm.put("idConductor", j.getConductor() != null ? j.getConductor().getIdUsuario() : "");
            jm.put("idRuta", j.getRuta() != null ? j.getRuta().getIdRuta() : "");
            res.add(jm);
        }
        return res;
    }

    public static Cooperativa cargarDatosJson() {
        if (!FileSystemStorage.getInstance().exists(ARCHIVO_JSON)) {
            return null;
        }
        try {
            try (InputStream is = FileSystemStorage.getInstance().openInputStream(ARCHIVO_JSON);
                 InputStreamReader isr = new InputStreamReader(is, "UTF-8")) {
                JSONParser parser = new JSONParser();
                Map<String, Object> map = parser.parseJSON(isr);
                
                String ruc = (String) map.get("ruc");
                String nombre = (String) map.get("nombre");
                String direccion = (String) map.get("direccion");
                Cooperativa c = new Cooperativa(ruc, nombre, direccion);

                // Cargar Flotas
                List<Map<String, Object>> flotasMap = (List<Map<String, Object>>) map.get("flotas");
                if (flotasMap != null) {
                    for (Map<String, Object> vm : flotasMap) {
                        String placa = (String) vm.get("placa");
                        String marca = (String) vm.get("marca");
                        String modelo = (String) vm.get("modelo");
                        String tipo = (String) vm.get("tipoCombustible");
                        String estado = (String) vm.get("estadoDeVehiculo");
                        if ("Eléctrico".equals(tipo) || "Electrico".equals(tipo)) {
                            c.getFlotas().add(new VehiculoElectrico(placa, marca, modelo, tipo, estado, 
                                (String) vm.get("idVehiculoElectrico"), toDouble(vm.get("capacidadBateria")), 
                                toDouble(vm.get("nivelCarga")), toInt(vm.get("ciclosDeCarga"))));
                        } else {
                            c.getFlotas().add(new VehiculoDiesel(placa, marca, modelo, tipo, estado, 
                                (String) vm.get("idVehiculoDiesel"), toDouble(vm.get("capacidadDeTanque")), 
                                toDouble(vm.get("nivelDeCombustible"))));
                        }
                    }
                }

                // Cargar Usuarios
                List<Map<String, Object>> usuariosMap = (List<Map<String, Object>>) map.get("usuarios");
                if (usuariosMap != null) {
                    for (Map<String, Object> um : usuariosMap) {
                        String id = (String) um.get("idUsuario");
                        String nom = (String) um.get("nombre");
                        String pass = (String) um.get("contrasena");
                        String tipo = (String) um.get("tipoUsuario");
                        boolean isActivo = true;
                        if (um.get("isActivo") != null) {
                            if (um.get("isActivo") instanceof Boolean) isActivo = (Boolean) um.get("isActivo");
                            else if (um.get("isActivo") instanceof String) isActivo = "true".equals(um.get("isActivo"));
                        }
                        
                        if ("CONDUCTOR".equals(tipo)) {
                            c.getUsuarios().add(new Conductor(id, nom, pass, tipo, isActivo, c, (String) um.get("licencia"), true));
                        } else if ("MONITOR".equals(tipo)) {
                            c.getUsuarios().add(new Monitor(id, nom, pass, tipo, isActivo, c, (String) um.get("idMonitor")));
                        } else {
                            c.getUsuarios().add(new GestorCooperativa(id, nom, pass, tipo, isActivo, c, (String) um.get("idGestor")));
                        }
                    }
                }

                // Cargar Rutas
                List<Map<String, Object>> rutasMap = (List<Map<String, Object>>) map.get("rutas");
                if (rutasMap != null) {
                    for (Map<String, Object> rm : rutasMap) {
                        Ruta r = new Ruta((String) rm.get("idRuta"), (String) rm.get("nombreDeRuta"), 
                            (String) rm.get("puntoDeSalida"), (String) rm.get("puntoDeLlegada"), 
                            (String) rm.get("horaDeSalida"), (String) rm.get("horaDeLlegada"), 
                            toInt(rm.get("duracion")), toDouble(rm.get("distancia")));
                        
                        List<Map<String, Object>> ptList = (List<Map<String, Object>>) rm.get("puntosDeControl");
                        if (ptList != null) {
                            for (Map<String, Object> pcm : ptList) {
                                Monitor monitorPc = null;
                                String idMon = (String) pcm.get("idMonitorControl");
                                if (idMon != null) {
                                    for (Usuario us : c.getUsuarios()) {
                                        if (us instanceof Monitor && us.getIdUsuario().equals(idMon)) {
                                            monitorPc = (Monitor) us;
                                            break;
                                        }
                                    }
                                }
                                PuntoControl pc = new PuntoControl((String) pcm.get("idControl"), (String) pcm.get("ubicacion"), (String) pcm.get("horaProgramada"), monitorPc);
                                pc.setHoraRealDePaso((String) pcm.get("horaRealDePaso"));
                                if (pcm.get("superado") instanceof Boolean) {
                                    pc.setSuperado((Boolean) pcm.get("superado"));
                                }
                                r.getPuntosDeControl().add(pc);
                            }
                        }
                        c.getRutas().add(r);
                    }
                }

                // Cargar Jornadas
                cargarJornadas(c, (List<Map<String, Object>>) map.get("jornadasActivas"), c.getJornadasActivas());
                cargarJornadas(c, (List<Map<String, Object>>) map.get("jornadasFinalizadas"), c.getJornadasFinalizadas());

                return c;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static void cargarJornadas(Cooperativa c, List<Map<String, Object>> jlist, List<Jornada> target) {
        if (jlist == null) return;
        for (Map<String, Object> jm : jlist) {
            Vehiculo vehiculo = null;
            for (Vehiculo v : c.getFlotas()) {
                if (v.getPlaca().equals(jm.get("idVehiculo"))) { vehiculo = v; break; }
            }
            Conductor conductor = null;
            for (Usuario u : c.getUsuarios()) {
                if (u.getIdUsuario().equals(jm.get("idConductor")) && u instanceof Conductor) { conductor = (Conductor) u; break; }
            }
            Ruta ruta = null;
            for (Ruta r : c.getRutas()) {
                if (r.getIdRuta().equals(jm.get("idRuta"))) { ruta = r; break; }
            }
            
            Jornada j = new Jornada((String) jm.get("numero"), (String) jm.get("fecha"), vehiculo, conductor, ruta);
            j.setHoraDeInicioRegistrado((String) jm.get("horaInicio"));
            j.setHoraDeFinRegistrado((String) jm.get("horaFin"));
            j.setEstadoDeJornada((String) jm.get("estado"));
            j.setKilometrosRecorridos(toDouble(jm.get("km")));
            j.setPasajerosTransportados(toInt(jm.get("pasajeros")));
            j.setCantidadDeViajes(toInt(jm.get("viajes")));
            
            if (conductor != null && "FINALIZADA".equals(j.getEstadoDeJornada())) {
                conductor.getJornadasRealizadas().add(j);
            }
            target.add(j);
        }
    }

    private static double toDouble(Object o) {
        if (o instanceof Double) return (Double) o;
        if (o instanceof Number) return ((Number) o).doubleValue();
        if (o instanceof String) {
            try { return Double.parseDouble((String)o); } catch(Exception e){}
        }
        return 0;
    }

    private static int toInt(Object o) {
        if (o instanceof Integer) return (Integer) o;
        if (o instanceof Number) return ((Number) o).intValue();
        if (o instanceof String) {
            try { return Integer.parseInt((String)o); } catch(Exception e){}
        }
        return 0;
    }
}
