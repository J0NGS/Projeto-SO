package mars.mips.instructions.syscalls;

import java.util.ArrayList;

import mars.ProcessingException;
import mars.ProgramStatement;
import mars.assembler.SourceLine;
import mars.mips.SO.ProcessManager.TabelaProcessos;
import mars.mips.hardware.RegisterFile;

public class SyscallFork extends AbstractSyscall{
    ProgramStatement statement;
    public SyscallFork(){
        super(60, "SyscallFork");
    }
    
    @Override
    public void simulate(ProgramStatement statement) throws ProcessingException {
        if(TabelaProcessos.getUltimoEnderecoPrograma() == 0) {
            int linhaChamada = statement.getSourceLine();
            int enderecoChamada = statement.getAddress();

            ArrayList<SourceLine> linhasPrograma = statement.getSourceMIPSprogram().getSourceLineList();
            int ultimaLinhaProgramaIndex = linhasPrograma.size() - 1;
            int ultimaLinhaPrograma = linhasPrograma.get(ultimaLinhaProgramaIndex).getLineNumber();

            int distanciaFinal = ultimaLinhaPrograma - linhaChamada;
            TabelaProcessos.setUltimoEnderecoPrograma(enderecoChamada + distanciaFinal*8);
        }

        TabelaProcessos.create(RegisterFile.getValue(4), RegisterFile.getValue(5));
    }
}
