package es.iesraprog2425.pruebaes.app

import es.iesraprog2425.pruebaes.log.LogHelper
import es.iesraprog2425.pruebaes.model.Operadores
import es.iesraprog2425.pruebaes.ui.IEntradaSalida
import java.text.SimpleDateFormat
import java.util.*

class Calculadora(private val ui: IEntradaSalida, private val logHelper: LogHelper) {

    private val sdf = SimpleDateFormat("yyyyMMddHHmmss")

    private fun pedirNumero(msj: String, msjError: String = "Número no válido!"): Double {
        val numero = ui.pedirDouble(msj)
        if (numero == null) {
            throw InfoCalcException(msjError)
        }
        return numero
    }

    private fun pedirInfo(): Triple<Double, Operadores, Double> {
        val num1 = pedirNumero("[+] Introduce el primer número: ", "El primer número no es válido!")
        val opChar = ui.pedirInfo("[+] Introduce el operador (+, -, *, /): ").firstOrNull()
        val operador = Operadores.getOperador(opChar)
            ?: throw InfoCalcException("[-] El operador no es válido!")
        val num2 = pedirNumero("[+] Introduce el segundo número: ", "El segundo número no es válido!")
        return Triple(num1, operador, num2)
    }

    private fun realizarCalculo(numero1: Double, operador: Operadores, numero2: Double): Double {
        return when (operador) {
            Operadores.SUMA -> numero1 + numero2
            Operadores.RESTA -> numero1 - numero2
            Operadores.MULTIPLICACION -> numero1 * numero2
            Operadores.DIVISION -> numero1 / numero2
        }
    }

    fun iniciar() {
        do {
            try {
                ui.limpiarPantalla()
                val (numero1, operador, numero2) = pedirInfo()
                val resultado = realizarCalculo(numero1, operador, numero2)
                val mensaje = "[+] Resultado: %.2f".format(resultado)
                ui.mostrar(mensaje)

                logHelper.log("${sdf.format(Date())} - Operación: $numero1 ${operador.simbolos[0]} $numero2 = %.2f".format(resultado))

            } catch (e: InfoCalcException) {

                ui.mostrarError(e.message ?: "[-] Se ha producido un error en los datos ingresados.")
                logHelper.log("${sdf.format(Date())} - Error: ${e.message}")

            } catch (e: NumberFormatException) {

                ui.mostrarError("[-] Formato numérico inválido.")
                logHelper.log("${sdf.format(Date())} - Error: Formato numérico inválido.")

            } catch (e: Exception) {

                ui.mostrarError("[-] Error inesperado: ${e.message}")
                logHelper.log("${sdf.format(Date())} - Error inesperado: ${e.message}")

            }
        } while (ui.preguntar())
        ui.limpiarPantalla()
    }
}
