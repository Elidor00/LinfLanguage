package lvm;

import lvm.parser.*;

public class LvmVisitorImpl extends LVMBaseVisitor {

    @Override
    public Void visitProgram(LVMParser.ProgramContext ctx){
        visitChildren(ctx);
        // TODO: label
        return null;
    }

    

}
