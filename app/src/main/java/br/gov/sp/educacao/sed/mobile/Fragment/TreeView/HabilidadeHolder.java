package br.gov.sp.educacao.sed.mobile.Fragment.TreeView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.unnamed.b.atv.model.TreeNode;

import java.text.SimpleDateFormat;
import java.util.Date;

import br.gov.sp.educacao.sed.mobile.Modelo.DiasLetivos;
import br.gov.sp.educacao.sed.mobile.Modelo.Habilidades;
import br.gov.sp.educacao.sed.mobile.Modelo.HabilidadesAbordadas;
import br.gov.sp.educacao.sed.mobile.Modelo.Turma;
import br.gov.sp.educacao.sed.mobile.QueryDB.DataBaseDAO.TableTO.UsuarioTO;
import br.gov.sp.educacao.sed.mobile.QueryDB.RegistroAulasQueryDB;
import br.gov.sp.educacao.sed.mobile.R;
import br.gov.sp.educacao.sed.mobile.Util.Queries;

/**
 * Created by techresult on 04/04/2016.
 */
public class HabilidadeHolder extends TreeNode.BaseNodeViewHolder<HabilidadeHolder.HabilidadeView> {

    public HabilidadeHolder(Context context) {
        super(context);
    }

    @Override
    public View createNodeView(TreeNode treeNode, final HabilidadeView value) {
        final LayoutInflater inflater = LayoutInflater.from(context);
        final View view = inflater.inflate(R.layout.item_habilidade_registro_aula, null, false);
        TextView tvHabilidade = (TextView) view.findViewById(R.id.tv_habilidade);
        tvHabilidade.setText(value.habilidade.getDescricaoHabilidade());

        final Turma turma = value.turma;
        final Habilidades habilidade = value.habilidade;
        final DiasLetivos diaLetivo = value.diaLetivo;

        CheckBox checkboxHabilidade = (CheckBox) view.findViewById(R.id.check_habilidade);
        checkboxHabilidade.setChecked(value.isChecked);

        final UsuarioTO usuario = Queries.getUsuarioAtivo(context);

        checkboxHabilidade.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    HabilidadesAbordadas habilidadesAbordadas = new HabilidadesAbordadas();
                    habilidadesAbordadas.setHabilidadeId(habilidade.getId());
                    habilidadesAbordadas.setDiasLetivosId(diaLetivo.getId());
                    habilidadesAbordadas.setUsuarioId(usuario.getId());
                    habilidadesAbordadas.setTurmaId(turma.getId());
                    habilidadesAbordadas.setDataCadastro(new SimpleDateFormat("dd/MM/yyyy hh:mm").format(new Date()));
                    //habilidadesAbordadas.setDescricao()

                    RegistroAulasQueryDB.setHabilidadesAbordadas(context, habilidadesAbordadas);
                }
                else{
                    //Remove a habilidade abordada
                    HabilidadesAbordadas habilidadesAbordadas = new HabilidadesAbordadas();
                    habilidadesAbordadas.setHabilidadeId(habilidade.getId());
                    habilidadesAbordadas.setDiasLetivosId(diaLetivo.getId());
                    habilidadesAbordadas.setUsuarioId(usuario.getId());
                    habilidadesAbordadas.setTurmaId(turma.getId());

                    if(!RegistroAulasQueryDB.deletaHabilidadeAbordada(context, habilidadesAbordadas)){
                        Toast.makeText(context, context.getResources().getString(R.string.nao_possivel_deletar_habilidade), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        return view;
    }

    public static class HabilidadeView{
        public boolean isChecked;
        public Turma turma;
        public Habilidades habilidade;
        public DiasLetivos diaLetivo;

        public HabilidadeView(boolean isChecked, Turma turma, Habilidades habilidade, DiasLetivos diaLetivo){
            this.isChecked = isChecked;
            this.turma = turma;
            this.habilidade = habilidade;
            this.diaLetivo = diaLetivo;
        }
    }

}
